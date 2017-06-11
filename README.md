# batch
Batch framework based on Spring for dealing with larger of data!
It can split into many business units,One business unit should extend AbstractBatchUnit.java,which has three generic parameters,such as,I,M,O.
I means input parameter,M means middle parameter which is converted by I,O means output parameter.
Not only it can support to handle database data now,it also will process file data in the future.
Workflow of business unit is that, data from database and files will wrap into ResultSet and FileSet,and then
they decorate to one single task which is needed to extend BaseTask.java, one single task can be consumed
by consumeTask method.Tow modes is provided to support this workflow.which are 'PC Mode' and 'Comm Mode'
named by me.'PC Mode' means that producer produces data and consumer consumes data.Of course,'Comm Mode'
means that one person both produce data and consume data.

Road Map:
1.One application can be deployed on multi-servers,every server's task is configured in advance!
2.One distributed batch application which is made of resource manager and business manager,resource manager focus on monitoring business manager's heartbeats and adjust business manager's task configuration dynamically!
3.One scheduling platform can provide multi-tenant batch to focus their business logic,tenant don't
need to care about batch's health.

Uufinishef...







