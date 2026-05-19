# Docker Basics

## Image

An image is a template used to create containers.

In this project:

- mongo:7 is the MongoDB image.

## Container

A container is a running instance of an image.

This project uses three MongoDB containers:

- mongo-ag
- mongo-hn
- mongo-oz

Each container simulates one database shard node.

## Docker Compose

Docker Compose manages multiple containers using a single configuration file.

In this project, docker-compose.yml starts all MongoDB shard nodes with one command:

docker compose up -d

## Current Containers

- mongo-ag: localhost:27017
- mongo-hn: localhost:27018
- mongo-oz: localhost:27019
