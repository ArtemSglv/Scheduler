#!/usr/bin/env bash

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
    target/Scheduler-1.0-SNAPSHOT.jar \
    $REMOTEUSER@$REMOTEHOST:$REMOTEAPPDIR

echo 'Bye'