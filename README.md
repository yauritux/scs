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

## Running/Testing in Local Machine

For those of you who's looking to test and run in a developer mode (i.e. local machine). You can harness the `docker-compose.yml` by following these steps below:

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

## Running/Testing in a Kubernetes Cluster (On-Prem)

For those of you who's curious to test it in a Kubernetes Cluster, particularly on simulating the On-Prem / Baremetal environment. In general there are few tasks that you need to perform as follow:
1. Setup your VM (at least 3 nodes are needed, 1 for the master node, and 2 for the worker nodes). For this purpose, I have prepared the `Vagrantfile` (under the `iac/onprem` folder) that you can harness to create these VMs. 
2. Install `docker` on each of those 3 nodes you created earlier. Get the instruction from [here](https://docs.docker.com/engine/install/ubuntu/).
3. Install `kubeadm`, `kubelet`, `kubectl`. Find the instruction [here](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/install-kubeadm/).
4. Create a kubernetes cluster using `kubeadm` in master node. Get the instruction from [here](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/create-cluster-kubeadm/)
5. Initialize the control plane and setup the pod network. Get the instruction from [here](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/create-cluster-kubeadm/#pod-network).
6. Setup your worker nodes to joining your new cluster. Get the instruction from [here](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/create-cluster-kubeadm/#pod-network).
7. Deploy the all services to our new created cluster. All deployment files can be found from these following folder:
   - `iac/application`
   - `iac/postgres`
   - `iac/kafka`
