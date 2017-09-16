git remote add heroku git@github.com:hackzurich-yabs/heroku.git || true
cd ..
git subtree push --prefix yabs-backend heroku master