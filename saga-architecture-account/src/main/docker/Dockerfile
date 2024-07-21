FROM @base.image@
COPY maven/@target.jar.filename@ /app/
WORKDIR /app
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime && echo Asia/Shanghai > /etc/timezone
EXPOSE 8080

CMD java ${JAVA_OPTS} -jar @target.jar.filename@