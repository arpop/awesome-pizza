L'applicazione non è completamente dockerizzatra. Per avviare l'ambiente eseguire:

```docker build -t awesome-pizza-db ./docker_db/</code>```

```docker-compose up --build```

```mvn spring-boot:start```

A questo punto l'app dovrebbe essere raggiungibile a http://localhost:8080
Nella root del progetto c'è anche una collection di postman.
