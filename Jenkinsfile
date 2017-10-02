node {
    stage("Checkout & Prepare") {
        checkout scm
    }
    stage("Build") {
        sh "chmod +x gradlew"
        sh "./gradlew clean build -x test"
    }
    stage("Unit tests") {
        sh "./gradlew test jacocoTestReport -i"
    }
    stage("Sonar") {
        sh "./gradlew sonarqube -i"
    }
    stage("Publish") {
        sh "./gradlew publish"
    }
}
