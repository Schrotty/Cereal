# Cereal

A totaly useless programming language which 'compiles' to java.

## Example
```
bowl myFirstCereal {
    _void fixed ingredient main(args: _string[]) {
        print(process(args[0]));
        print(calc(5, 20));
    }

    _string fixed ingredient process(str: _string) {
        return str;
    }

    _int fixed ingredient calc(x: _int, y: _int) {
        return y + x;
    }
}
```

## Usage
Build the project using maven and execute ``` java -jar cereal2java.jar <cereal_source_file>```

## License
[MIT](https://github.com/Schrotty/Cereal/blob/master/LICENSE)