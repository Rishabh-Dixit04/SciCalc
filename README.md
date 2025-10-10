# Scientific Calculator with Full DevOps Pipeline (CS 816 Mini Project)

This project implements a command-line Scientific Calculator in Java and wraps the entire development lifecycle within a complete DevOps toolchain, demonstrating Continuous Integration (CI) and Continuous Deployment (CD).

The application provides a user menu for the following operations:
* Square root ($\sqrt{x}$)
* Factorial ($x!$)
* Natural Logarithm (ln(x))
* Power ($x^b$)

The project utilizes a toolchain including **GitHub** (SCM), **Maven/JUnit** (Build/Test), **Jenkins** (CI), **Docker** (Containerization), and **Ansible** (Deployment).

---

## Running the Application (Deployment)

Since the application is fully containerized, the easiest way to run and interact with it is by using Docker, either directly or via the final Ansible deployment script.

### Prerequisites

1.  **Docker:** Must be installed and running on your system.
2.  **Ansible:** Must be installed (if deploying via the playbook).
3.  **Community Docker Collection:** `ansible-galaxy collection install community.docker`

### Method 1: Deploying via Ansible (The CI/CD Way)

This method replicates the final deployment step of the pipeline, ensuring you run the exact container image pushed by Jenkins.

1.  **Create Inventory:** Ensure the `inventory.ini` file exists in the project root:
    ```ini
    [calculator_hosts]
    localhost ansible_connection=local
    ```

2.  **Run Deployment:** Execute the Ansible playbook. This will pull the latest image, stop any old container, and start a new interactive one.
    ```bash
    ansible-playbook -i inventory.ini ansible-playbook.yml
    ```

3.  **Access the Running Calculator:** Once the playbook succeeds, use `docker exec` to attach to the persistent container and run the Java application:
    ```bash
    docker exec -it scientific_calculator_app java -jar app.jar
    ```

4.  **Exit:** To detach, press `Ctrl+C` inside the container session.

### Method 2: Running Directly with Docker

You can pull and run the image directly from Docker Hub.

1.  **Pull the Image:**
    ```bash
    docker pull rishabh720/scientific-calculator:latest
    ```

2.  **Run in Interactive Mode:** Start the container, allocating a TTY (`-t`) and keeping STDIN open (`-i`) to allow for user input.
    ```bash
    docker run -it --rm --name temp_calculator rishabh720/scientific-calculator:latest java -jar app.jar
    ```

---

## The DevOps Pipeline Stages

The pipeline is defined in `Jenkinsfile` and executes the following stages automatically upon every code push:

1.  **Pull Code:** Retrieves the latest changes from the GitHub repository.
2.  **Run Test and Build:** Executes Maven to run JUnit tests and creates the executable JAR artifact.
3.  **Build Docker Image:** Creates the final application image using the multi-stage `Dockerfile`.
4.  **Push to Docker Hub:** Authenticates and pushes the tested image to the public registry.
5.  **Deployment (Manual Trigger):** The tested image is deployed to the local host using the Ansible playbook.

---

To know more about the internal working of the project, including technical configurations and justification for tool choices, refer to the [project report](https://github.com/Rishabh-Dixit04/SciCalc/blob/master/Project_Report.pdf).
