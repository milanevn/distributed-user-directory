# Distributed User Directory

Final project for Distributed Database Systems.

## Topic

Global User Directory using range-based sharding.

## Project Idea

This project simulates a distributed user directory where user data is horizontally fragmented across multiple MongoDB shard nodes.

Users will be routed to different shards based on the first letter of their username.

## Initial Shard Ranges

- A-G: shard_ag
- H-N: shard_hn
- O-Z: shard_oz

## Current Implementation Status

- React frontend initialized
- Spring Boot backend initialized
- Docker Compose configured
- Three MongoDB containers running as shard nodes
- Backend connected to all three MongoDB shards
- Manual test APIs created for inserting users into each shard

## Tech Stack

- React
- Spring Boot
- MongoDB
- Docker Compose

## Core Concepts

- Distributed database
- Horizontal fragmentation
- Range-based sharding
- Data skew
- Hotspot detection
- Re-sharding
- Fault handling
