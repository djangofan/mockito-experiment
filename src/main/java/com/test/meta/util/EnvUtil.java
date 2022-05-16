package com.test.meta.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Will make a call to System.getenv , and if it fails, will fail-over to local.settings.json .
 */
@Slf4j
public class EnvUtil {

    private static final String LOCAL_SETTINGS_FILE = "local.settings.json";
    private static final String DEFAULT_LOCAL_SETTINGS = "{\"IsEncrypted\": false,\"Values\": {}}";
    private static final String ENV_SETTINGS_KEY = "Values";
    private static final String ENCODING = "UTF-8";

    public enum AppVar {
        CODE_SUCCESS,
        CODE_ERROR,
        APPINSIGHTS_INSTRUMENTATIONKEY,
        APP_CONFIGURATION_CONNECTION_STRING,
        COSMOS_DB_NAME,
        COSMOS_DB_CONTAINER,
        COSMOS_ACCOUNT_HOST,
        COSMOS_ACCOUNT_KEY,
        resource_group_name,
        SSL_CERT_PRIVATE_KEY
    }

    public static String getProperty(AppVar propertyName) {
        Optional<String> optionalConnectionString = Optional.ofNullable(System.getenv(propertyName.name()));
        return optionalConnectionString.orElse(readPropertyFromLocalConfig(propertyName.name(), ENV_SETTINGS_KEY));
    }

    private static String readPropertyFromLocalConfig(String propertyName, String parentKey) {
        try (FileInputStream fis = new FileInputStream(LOCAL_SETTINGS_FILE)) {
            Optional<String> optionalData = Optional.ofNullable(IOUtils.toString(fis, ENCODING));
            JSONObject jsonObject = new JSONObject(optionalData.orElse(DEFAULT_LOCAL_SETTINGS)).getJSONObject(parentKey);
            if (jsonObject.has(propertyName)) {
                return (String)jsonObject.get(propertyName);
            }
            return StringUtils.EMPTY;
        } catch (JSONException | IOException e) {
            log.debug("ENV property '" + propertyName + "' is not defined in file '" + LOCAL_SETTINGS_FILE + "'.  Must load from system environment instead.");
        }
        return StringUtils.EMPTY;
    }

    public static Map<String, String> getProperties() {
        Map<String, String> values = new HashMap<>();
        for (AppVar prop : AppVar.values()) {
            values.put(prop.name(), getProperty(prop));
            log.debug("KEY: " + prop.name() + ", VAL: " + getProperty(prop));
        }
        return values;
    }

}
