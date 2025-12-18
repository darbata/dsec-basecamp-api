# DSEC Basecamp API *WIP*

Basecamp is a web application available to all DSEC members that encourages collaboration and learning outside of the classroom.

This repository stores the source code for an API for DSEC Basecamp. Built to enable applications which interact with DSEC member generated content.

## Quick Start (Local Development)

1. run `docker-compose up -d`
2. make api calls to `http://localhost:8000/`
3. reference documentation via... (to be implemented with OpenAPI)

Currently database is not seeded with any data, optionally use [AWS CLI](https://aws.amazon.com/cli/) for development.

## Acquiring Authentication

To call any endpoint from this API a JWT token is required, acquired from AWS Cognito.

**Auth URL:** `https://ap-southeast-2r46bdzlr2.auth.ap-southeast-2.amazoncognito.com/login`
**Access Token URL:** `https://ap-southeast-2r46bdzlr2.auth.ap-southeast-2.amazoncognito.com/oauth2/token`

## Tech Stack
- Java 21
- Spring Boot
- AWS Cognito (JWT)
- Spring Security
- AWS DynamoDB (NoSQL)

## Architecture

The logical architecture of the application follows [Hexagonal (Ports and Adaptors) Architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)).