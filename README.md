## Tiny JWT library
I create this library to use in my servlet-based [project](https://github.com/beljaeff/simple-jwt-servlet-auth) with JWT auhorization.
In fact, it is a very early version created just for fun, but it is ready to use.
Library uses HMAC (SHA256, SHA384, SHA512) for signing, base64 from java.util and [jackson](https://github.com/FasterXML/jackson) library to work with JSON.
Library designed for easy extending with another signing implementations, JSON serialization and deserialization, base64 encoding and decoding.

## Maven usage
Put artifact to your local repository:
```
git clone https://github.com/beljaeff/tiny-jwt
cd tiny-jwt
mvn install
```
Then you can use it in your project:
```
        <dependency>
            <groupId>us.cleansite.very</groupId>
            <artifactId>tiny-jwt</artifactId>
            <version>0.0.1</version>
        </dependency>
```

## Code example
```
public class Example {

    private static final String KEY = "SuperSecretKey";

    public static class MyPayload extends Payload {
        private String data;

        public MyPayload() {
        }

        MyPayload(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        MyPayload payload = new MyPayload("some data");
        String jwtToken = Jwt.encoder()
                .payload(payload)
                .key(KEY)
                .encode();

        System.out.println(jwtToken);

        MyPayload decoded = Jwt.decoder()
                .key(KEY)
                .token(jwtToken)
                .decode(MyPayload.class);

        System.out.println(decoded.getData());
    }
}
```
`Payload` class is used for transferring JWT data, so you can use it or extend it.
Library checks `nbf` and `exp` fields of JWT given.
You can see [wiki](https://en.wikipedia.org/wiki/JSON_Web_Token) or read the code for JWT fields decription.

## License
GPL, you can free modify and share this library.
