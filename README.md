# kafka-demo project with OpenShift pipelines quarkus native build and GitOps

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Build with OpenShift Pipelines

To build a ubi based quarkus native image follow these instructions:

1. install the `Red Hat OpenShift Pipelines` [Operator](https://cloud.redhat.com/learn/topics/ci-cd)
2. apply the Pipeline: `oc apply -f src/main/pipeline/pipeline.yaml`
3. build your quarkus native image 
```
tkn pipeline start kafka-demo-pipeline \
-w name=shared-workspace,volumeClaimTemplateFile=src/main/pipeline/pipeline-pvc.yaml \
-p git-url=$(your-git-repo) \
-p IMAGE=image-registry.openshift-image-registry.svc:5000/kafka-demo/quarkus-kafka-demo-native \
--use-param-defaults
```
4. check your image stream with `oc get is` should output something like this
```
NAME                        IMAGE REPOSITORY                                                                        TAGS     UPDATED
quarkus-kafka-demo-native   image-registry.openshift-image-registry.svc:5000/kafka-demo/quarkus-kafka-demo-native   latest   6 hours ago
```

## Preparte AMQ Streams Kafka Environment

To deploy the kafka cluster with topic and user apply the following resources: 

- The kafka cluster: `src/main/kafka/kafka.yaml`
- The topic: `src/main/kafka/demo-topic.yaml`
- The user: `src/main/kafka/demo-user.yaml`
- The secret with information for the application where to find our kafka bootstrap server: `src/main/kafka/secret.yaml`

## Deploy it on OpenShift

To deploy the image apply the following resources:

- `src/main/openshift/deployment-config.yaml`
- `src/main/openshift/route.yaml`
- `src/main/openshift/service.yaml`

## CI/CD with Red Hat OpenShift GitOps Operator

*comming soon*

## Local Development
### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

### Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it???s not an _??ber-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _??ber-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

### Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/kafka-demo-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.
