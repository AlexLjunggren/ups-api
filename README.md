## UPS API Tools ##

**Note:** Register for API keu here: https://www.ups.com/upsdeveloperkit

## Instantiate ##

```java
UpsApi upsApi = new UpsApi(environment, username, password, accessKey);
```

Environment (PRODUCTION, CIE)

**Note:** CIE (Customer Integration Environment) is UPS's sandbox

```java
UpsEnvironment environment = UpsEnvironment.CIE;
```

## Track Package ##

```java
UpsResponse upsResponse = upsApi.track("7798339175");
```

