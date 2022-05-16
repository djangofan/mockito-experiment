# func-meta-test-api

TODO description

## Building And Execution

    mvn clean package 
    mvn azure-functions:run

### Local Development

You might need these:

    brew tap azure/functions
    brew install azure-functions-core-tools@3
    brew link --overwrite azure-functions-core-tools@3
    npm install -g azure-functions-core-tools@3 --unsafe-perm true

To deploy your local build to your own personal private function app instance, add
these lines to your `.bash_profile`.   These variables get passed through to the Maven
Azure plugin in your `pom.xml` :

    #PAS secrets
    export AZ_REGION=westus
    export FN_APP_NAME=fn-java-example
    export RG_NAME=example-rg
    export ASP_NAME=fnexample-asp

This will allow you to use `mvn package azure-functions:deploy`, for your own personal testing.

