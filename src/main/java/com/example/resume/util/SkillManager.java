package com.example.resume.util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.*;

public class SkillManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/resumeDB";
    private static final String USER = "pg";
    private static final String PASSWORD = "pg";

    public SkillManager() {


    }

    public static String getSkillsFromD(String positionName) {
        String jsonSkills = "{\n" +
                "  \"skills\": [\n" +
                "    \"Java\", \"J2EE\", \"Spring Framework\", \"Spring Boot\", \"Hibernate\", \"RESTful APIs\",\n" +
                "    \"SOAP\", \"Microservices\", \"SQL\", \"NoSQL\", \"MySQL\", \"PostgreSQL\", \"MongoDB\",\n" +
                "    \"Oracle Database\", \"JDBC\", \"JPA\", \"Maven\", \"Gradle\", \"JUnit\", \"Mockito\",\n" +
                "    \"Docker\", \"Kubernetes\", \"Git\", \"GitHub\", \"Bitbucket\", \"Jenkins\", \"CI/CD\",\n" +
                "    \"Agile\", \"Scrum\", \"Kanban\", \"Apache Kafka\", \"RabbitMQ\", \"Message Queues\",\n" +
                "    \"Multithreading\", \"Concurrency\", \"Data Structures\", \"Algorithms\", \"Design Patterns\",\n" +
                "    \"SOLID Principles\", \"TDD (Test Driven Development)\", \"DDD (Domain Driven Design)\",\n" +
                "    \"Clean Code\", \"Code Review\", \"Unit Testing\", \"Integration Testing\", \"System Testing\",\n" +
                "    \"Performance Tuning\", \"Profiling\", \"Memory Management\", \"Garbage Collection\",\n" +
                "    \"Java Virtual Machine (JVM)\", \"Cloud Platforms (AWS, Azure, GCP)\", \"APIs\", \"JSON\",\n" +
                "    \"XML\", \"Version Control\", \"Shell Scripting\", \"Linux\", \"Unix\", \"Windows\", \"Networking\",\n" +
                "    \"Security\", \"Encryption\", \"Authentication\", \"Authorization\", \"OAuth\", \"JWT\", \"LDAP\",\n" +
                "    \"SSO\", \"DevOps\"\n" +
                "  ]\n" +
                "}";



        try {
            // Проверка и создание таблицы и процедур
            initializeDatabase();

            // Сохранение JSON данных, если таблица пуста
            storeSkillsIfEmpty(jsonSkills, positionName);

            // Загрузка JSON данных
            String loadedSkills = loadSkillsByPosition(positionName);
            System.out.println("Loaded Skills for position " + positionName + ": " + loadedSkills);
       return loadedSkills;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void initializeDatabase() throws SQLException {
        String createSchemaSQL = "CREATE SCHEMA IF NOT EXISTS resume";

        String createPositionsTableSQL = "CREATE TABLE IF NOT EXISTS resume.positions (" +
                "id SERIAL PRIMARY KEY, " +
                "position_name VARCHAR(255) NOT NULL UNIQUE" +
                ")";

        String createSkillsTableSQL = "CREATE TABLE IF NOT EXISTS resume.skills (" +
                "id SERIAL PRIMARY KEY, " +
                "skills_json JSONB, " +
                "position_id INTEGER, " +
                "FOREIGN KEY (position_id) REFERENCES resume.positions (id)" +
                ")";

        String createStoreProcedureSQL = "CREATE OR REPLACE FUNCTION resume.store_skills(skills_data JSONB, pos_name VARCHAR) " +
                "RETURNS VOID AS $$ " +
                "DECLARE " +
                "    position_id INTEGER; " +
                "BEGIN " +
                "    SELECT id INTO position_id FROM resume.positions WHERE position_name = pos_name; " +
                "    IF NOT FOUND THEN " +
                "        INSERT INTO resume.positions (position_name) VALUES (pos_name) RETURNING id INTO position_id; " +
                "    END IF; " +
                "    INSERT INTO resume.skills (skills_json, position_id) VALUES (skills_data, position_id); " +
                "END; " +
                "$$ LANGUAGE plpgsql";

        String createLoadProcedureSQL = "CREATE OR REPLACE FUNCTION resume.load_skills_by_position(pos_name VARCHAR) " +
                "RETURNS JSONB AS $$ " +
                "DECLARE " +
                "    skills_data JSONB; " +
                "BEGIN " +
                "    SELECT skills_json INTO skills_data FROM resume.skills s " +
                "    JOIN resume.positions p ON s.position_id = p.id " +
                "    WHERE p.position_name = pos_name " +
                "    ORDER BY s.id DESC LIMIT 1; " +
                "    RETURN skills_data; " +
                "END; " +
                "$$ LANGUAGE plpgsql";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createSchemaSQL);
            stmt.execute(createPositionsTableSQL);
            stmt.execute(createSkillsTableSQL);
            stmt.execute(createStoreProcedureSQL);
            stmt.execute(createLoadProcedureSQL);
            System.out.println("Database initialized successfully.");
        }
    }

    public static void storeSkillsIfEmpty(String skillsJson, String positionName) throws SQLException {
        String checkTableSQL = "SELECT COUNT(*) FROM resume.skills";
        String insertSkillsSQL = "CALL resume.store_skills(?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement checkStmt = conn.createStatement();
             ResultSet rs = checkStmt.executeQuery(checkTableSQL)) {

            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSkillsSQL)) {
                    insertStmt.setString(1, skillsJson);
                    insertStmt.setString(2, positionName);
                    insertStmt.executeUpdate();
                    System.out.println("Skills stored successfully.");
                }
            } else {
                System.out.println("Table is not empty, no skills were stored.");
            }
        }
    }

    public static String loadSkillsByPosition(String positionName) throws SQLException {
        String sql = "SELECT resume.load_skills_by_position(?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, positionName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                } else {
                    throw new SQLException("No skills found for the position: " + positionName);
                }
            }
        }
    }
}
