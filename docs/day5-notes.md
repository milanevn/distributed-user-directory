# Day 5 Notes

## Goal

Generate a skewed dataset based on the project requirement.

## Requirement

The dataset should contain users where 70% of usernames start with M or S.

## Completed Work

- Created DatasetGenerationResponse DTO
- Added clearAllUsers method in UserService
- Added countUsersInShard method in UserService
- Created DatasetGeneratorService
- Created DatasetController
- Added POST /api/dataset/generate
- Tested dataset generation
- Verified shard distribution

## API

POST /api/dataset/generate?size=10000&clear=true

## Dataset Distribution Logic

- 70% usernames start with M or S
- 30% usernames start with other random letters

## Why M and S Matter

Current shard ranges:

- A-G -> shard_ag
- H-N -> shard_hn
- O-Z -> shard_oz

M belongs to H-N.

S belongs to O-Z.

Therefore, generating many M and S users creates an intentionally skewed distribution.

## Important Implementation Rule

The generator does not insert directly into MongoDB.

It calls UserService.createUser so that the normal shard routing logic is used.

This proves that the routing layer works under larger datasets.

## Meaning

This dataset prepares the project for hotspot analysis.
