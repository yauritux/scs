# SCS
SCS stands for Single-Core-Service, is one of the core service module being developed for BeaCukai (Customs). 

This repository was built merely for POC purposes to show how the Event-Sourcing and CQRS architecture work, by showing some simple business cases in SCS. 

However, we're also trying to simulate a real production environment by having all services deployed in a K8S cluster either within AWS-EKS or Baremetal (On-Prem). 

## Prerequisites

These are all you need to have in your local machine:
- Java 11
- Docker
- `Vagrant` (optional. Mandatory if you're looking to deploy in a K8S cluster with 1 master node and 2 worker nodes)
- `eksctl` (optional. Mandatory if you're looking to deploy all services in a EKS cluster)

## Running in Local Machine

1. `cd` into project directory.
2. Execute `docker-compose up`.
3. Compile all project modules.
   ```
   $ mvn clean install
   ```
5. Run the **command service** 
   ```
   $ cd scs-command && mvn spring-boot run
   ```
7. Run the **query service** 
   ```
   $ cd scs-query && mvn spring-boot run
   ```
