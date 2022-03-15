#Appcenter Java Appium PoC

* build `mvn -DskipTests -P prepare-for-upload package`  
* upload & run `appcenter test run appium --app "dozken/gsg" --devices 6adb7c80 --app-path myexpo.apk --test-series "master" --locale "en_US" --build-dir target/upload`

![](ss.png)
