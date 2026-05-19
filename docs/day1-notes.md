# Day 1 Notes

## Goal

Set up the project foundation and architecture.

## Completed Work

- Created local project repository
- Created GitHub repository
- Created Spring Boot backend
- Created React Vite frontend
- Created Docker Compose file
- Started three MongoDB containers
- Created initial project structure

## Distributed Database Concept

A distributed database is logically one database system, but its data is physically distributed across multiple nodes.

In this project, the user directory is treated as one logical system, while user data is stored across multiple MongoDB shard nodes.

## Horizontal Fragmentation

Horizontal fragmentation means splitting data by rows or documents.

In this project, user documents are distributed based on username ranges.

## Initial Architecture

React Frontend communicates with Spring Boot Backend.

Spring Boot will act as a shard router and communicate with three MongoDB shard nodes:

- mongo-ag
- mongo-hn
- mongo-oz

## Backend-only Scope Update

The React frontend was created during the initial setup, but the project scope has been changed to backend-only.

The final demo will use:

- Postman or curl
- Spring Boot APIs
- MongoDB Compass
- Docker logs
- Terminal output

This keeps the project focused on distributed database concepts instead of frontend implementation.
