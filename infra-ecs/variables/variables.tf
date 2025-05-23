variable "region" {
  default = "us-east-1"
}

variable "cluster_name" {
  default = "clara-ecs-cluster"
}

variable "task_family" {
  default = "clara-task"
}

variable "container_name" {
  default = "clara-container"
}

variable "image_url" {
  description = "864981729683.dkr.ecr.us-east-1.amazonaws.com/espaco-academico:latest"
  type        = string
}

variable "container_port" {
  default = 8080
}

variable "cpu" {
  default = "256"
}

variable "memory" {
  default = "512"
}

variable "service_name" {
  default = "clara-ecs-service"
}

variable "subnet_ids" {
  type = list(string)
}

variable "security_group_ids" {
  type = list(string)
}
