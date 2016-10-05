a trial to follow uncle bob's clean architecture

added a domain layer(the GetReposUseCase), so the presenter would use it instead of directly using the api layer.
I didn't find it much useful...maybe because the presenter's work isn't complex.
and I didn't like having the use case handle the multi threading