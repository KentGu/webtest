#!/bin/bash

. ./test.fun

if test $1; then
  echo "input is 1"
else
  echo "input isn't 1"
fi

echo "function return:" `test $1`
