# Distributed User Directory

Backend-only final project for Distributed Database Systems.

## Topic

Global User Directory using range-based sharding.

## Project Idea

This project simulates a distributed database system where user data is horizontally fragmented across multiple MongoDB shard nodes.

The Spring Boot backend acts as the routing layer. It decides which shard should store or query a user based on the first letter of the username.

## Core Architecture

Client tools:

- Postman
- curl
- browser for GET APIs
- MongoDB Compass for data inspection

Backend:

- Spring Boot

Database nodes:

- mongo-ag
- mongo-hn
- mongo-oz

## Initial Shard Ranges

- A-G: shard_ag
- H-N: shard_hn
- O-Z: shard_oz

## Tech Stack

- Java 17
- Spring Boot
- MongoDB
- Docker Compose
- Postman or curl

## Core Concepts

- Distributed database
- Horizontal fragmentation
- Range-based sharding
- Data localization
- Data skew
- Hotspot detection
- Re-sharding
- Failure handling

## Current Status

- Backend initialized
- Docker Compose configured
- Three MongoDB containers running as shard nodes
- Backend connected to all MongoDB shards
- Manual test APIs can insert data into each shard

## Note

A React frontend was initially created, but it is not part of the core demo anymore. The project will be demonstrated using backend APIs, Postman/curl, terminal logs, and MongoDB Compass.
