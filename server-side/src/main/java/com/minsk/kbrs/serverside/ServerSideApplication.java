package com.minsk.kbrs.serverside;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude={ DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ServerSideApplication {

    /**
     * сервер:
     * а. хранит текстовые файлы
     * б. генерирует случайный сеансовый ключ по запросу клиента
     * в. отправляет клиенту зашифрованный сеансовым ключом текстовый файл
     * г. отправляет зашифрованный открытым ключом RSA сеансовый ключ
     *
     * клиентская часть:
     * а. генерирует и отправляет серверу открытый ключ RSA (единожды)
     * б. генерирует и сохраняет у себя закрытый ключ RSA
     * в. отправляет серверу запрос с именем файла
     * г. расшифровывает закрытым ключом сеансовый ключ и отображает им же расшифрованный текстовый файл
     *
     * target:
     * AES, CFB
     *
     * */
    public static void main(String[] args) {
        SpringApplication.run(ServerSideApplication.class, args);
    }

}
