package br.edu.ufersa.avance.projetoavance.util;

import jakarta.persistence.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "Avance";
    private static final EntityManagerFactory EMF;

    static {
        try {
            // 1. Carrega as propriedades do arquivo config.properties
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));

            // 2. Configura as propriedades do JPA
            Properties jpaProps = new Properties();
            jpaProps.put("jakarta.persistence.jdbc.url", props.getProperty("db.url"));
            jpaProps.put("jakarta.persistence.jdbc.user", props.getProperty("db.user"));
            jpaProps.put("jakarta.persistence.jdbc.password", props.getProperty("db.password"));

            // 4. Cria o EntityManagerFactory com as propriedades personalizadas
            EMF = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, jpaProps);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar config.properties", e);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar EntityManagerFactory", e);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return EMF;
    }

    public static void shutdown() {
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }
}