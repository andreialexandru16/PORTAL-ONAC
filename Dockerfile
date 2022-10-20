FROM java:8
ADD ./target/documenta-ocr-review-checkout-0.0.1-SNAPSHOT.jar documenta-ocr-review-checkout-0.0.1-SNAPSHOT.jar
RUN bash -c 'touch documenta-ocr-review-checkout-0.0.1-SNAPSHOT.jar'
RUN mkdir -p /logdata
VOLUME /logdata
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar documenta-ocr-review-checkout-0.0.1-SNAPSHOT.jar
EXPOSE 8080
