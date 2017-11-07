node {
    stage("Checkout & Prepare") {
        checkout scm
    }
    stage("Build") {
        sh "./gradlew clean build -x test -x checkstyleMain -x checkstyleTest"
    }
    stage("Check code style") {
        sh "./gradlew checkstyleMain checkstyleTest -x test"
    }
    stage("Unit tests") {
        sh "./gradlew test jacocoTestReport -i"
    }
    stage("Integration tests") {
        sh "./gradlew :bitcoin-integration-tests:test -Prun-integr-test=true -i"
    }
    stage("Sonar") {
        sh "./gradlew sonarqube -i"
    }
    stage("Publish") {
        sh "./gradlew publish"
    }
}
