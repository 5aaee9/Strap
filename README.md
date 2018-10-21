# Strap
Yet, another Telegram Bot Framework

[![](https://jitpack.io/v/Indexyz/Strap.svg)](https://jitpack.io/#Indexyz/Strap)

## Create First Plugin
```java
import me.indexyz.strap.annotations.Command;
import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.define.CommandContext;

@Events
public class FirstPlugin {
    @Command("start")
    public static void start(CommandContext context) {
        context.reply("Hello, World");
    }
}
```

Build it and put jar in plugins. Then run strap with java.

After first run, edit `config.properties` and put your bot Token into it.

Then it will response your command!

## Gradle Plugin
```grovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.Indexyz:Strap:master-SNAPSHOT'
}
```