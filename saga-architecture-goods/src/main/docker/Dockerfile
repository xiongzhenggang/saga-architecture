FROM @base.image@
COPY maven/@target.jar.filename@ /app/
WORKDIR /app
EXPOSE 8080

CMD java ${JAVA_OPTS} -jar @target.jar.filename@