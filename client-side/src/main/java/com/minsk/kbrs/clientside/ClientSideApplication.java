package com.minsk.kbrs.clientside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class ClientSideApplication {

    /**
     * клиентская часть:
     * а. генерирует и отправляет серверу открытый ключ RSA (единожды)
     * б. генерирует и сохраняет у себя закрытый ключ RSA
     * в. отправляет серверу запрос с именем файла
     * г. расшифровывает закрытым ключом сеансовый ключ и отображает им же расшифрованный текстовый файл
     */
    public static void main(String[] args) {
        SpringApplication.run(ClientSideApplication.class, args);
    }

}
