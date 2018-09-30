# Cereal

A totaly useless programming language which 'compiles' to java.

## Example
```
common bowl myFirstCereal {

    important {
    
        /* NAME OF THE CEREAL */
        _String myName << "Herman";

        <!--
        public static void mainer(String name) {
            return String.format("Hello %s", name);
        }
        --!>

        common ingredient main(args: _String[]) => _void {        
            print(test());
        }

        delicious ingredient test() => _String {
            add mySecondCereal("I love cookies!");

            return "mySecondCereal";
        }
        
        post {
            delicious ingredient postMessage() {
                print("I'm done!");
            }
        }
    }
}

bowl mySecondCereal {
    important {
        common ingredient helloThere() => _String {
            return "Hello there!";
        }
    }

    common ingredient mySecondCereal(str: _String) {
        print("That's not my string!");
    }
    
    common ingredient myName() => _String {
        return "myName";
    }
}
```

compiles to

```java
public class myFirstCereal {
    static String myName="Herman";
    public static void main(String[] args) {
        System.out.println(test());
    }

    private static String test() {
        new mySecondCereal("I love cookies!");
        return "mySecondCereal";
    }

    private static void postMessage() {
        System.out.println("I'm done!");
    }
}

class mySecondCereal {
    public static String helloThere() {
        return "Hello there!";
    }
    public mySecondCereal(String str) {
        System.out.println("That's not my string!");
    }

    public  String myName() {
        return "myName";
    }
}

```

## Usage
Build the project using maven and execute ``` java -jar cereal2java.jar <cereal_source_file>```

## License
[MIT](https://github.com/Schrotty/Cereal/blob/master/LICENSE)