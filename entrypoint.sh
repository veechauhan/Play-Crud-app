#!/bin/bash

./play-rest-api-crud-1.0-SNAPSHOT/bin/play-rest-api-crud \
-Dslick.dbs.default.db.properties.url="jdbc:postgresql://${DB_URL}:${PORT}/postgres?currentSchema=public" \
-Dslick.dbs.default.db.url="jdbc:postgresql://${DB_URL}:${PORT}/postgres" \
-Dslick.dbs.default.db.username="${USER}" \
-Dslick.dbs.default.db.password="${PASS}" \
-Dplay.http.secret.key=$PLAY_SECRET