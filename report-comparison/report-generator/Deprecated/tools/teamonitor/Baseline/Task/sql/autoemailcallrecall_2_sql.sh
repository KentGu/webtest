#!/bin/bash
select_sql="select count(1) from colagreementheader a, COLSTATEMENTSTATUS s where a.id = s.agreementid and s.statusid =68 and a.region = 'New York';"
sql_description="Number of Agreement statementstatus approved in New York is:"

export select_sql sql_description
