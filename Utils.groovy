package org.example

class Utils implements Serializable {
    def steps

    Utils(steps) {
        this.steps = steps
    }

    def exampleMethod() {
        steps.echo 'Hello from Utils.groovy!'
    }
}
