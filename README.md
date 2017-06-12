# batch
Batch framework based on Spring for dealing with larger of data!
It can be split into many business units,one business unit should extend AbstractBatchUnit.java,
which has three generic parameters,such as,I,M,O. I means input parameter,M means middle parameter
which is converted by I,O means output parameter.Not only it can support to handle database data now,
it also will process file data in the future.Workflow of business unit is that, data from database and
files will wrap into ResultSet and FileSet,and then they decorate to one single task which is needed to
extend BaseTask.java, one single task can be consumed by consumeTask method.'PC Mode' and 'Comm Mode' are provided to support this workflow.'PC Mode' means that producer produces data
and consumer consumes data.Of course,'Comm Mode' means that one person both produce data and consume data.

Unfinished...






