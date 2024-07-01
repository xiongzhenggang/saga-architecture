FROM @base.image@
COPY maven/@target.jar.filename@ /app/

USER root
RUN groupadd appuser && useradd -g appuser appuser && chown -R appuser:appuser /app

user appuser:appuser \
WORKDIR /app
EXPOSE 8080

CMD java ${JAVA_OPTS} -jar @target.jar.filename@