APP=infinite-citadel-3625

pull:
	git fetch --prune && git rebase origin/master

push:
	git push origin master

run-dev:
	lein run -m clj-jam.web 5000

deploy:
	git push heroku master

install-heroku:
	git remote add heroku git@heroku.com:infinite-citadel-3625.git

login:
	heroku login

ps:
	heroku ps --app $(APP)

open:
	heroku open --app $(APP)

scale:
	heroku ps:scale web=1 --app $(APP)

logs:
	heroku logs --app $(APP)

apps:
	heroku apps

remote-repl:
	heroku run lein repl

tests:
	lein test
