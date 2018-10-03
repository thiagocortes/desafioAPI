# desafioAPI

Api para desafio proposto;
Projeto criado em Java e utilizando fremework Spring Boot 2.0.5

# Pré requisitos

Java 8
Tomcat 8

# Uso

Atualmente api só permite ser acessado pelo endereço 'http://localhost:4200' e os métodos GET e POST, caso queira alterar, basta ir na classe com.cortes.desafio.solutis.config.CORSConfig e fazer as alterações desejadas. Ex.: Para permitir qualquer dominio e métodos mude para addAllowedOrigin("*") e addAllowedMethod("*");

Faça o deploy e faça bom uso.
