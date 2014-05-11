-- Creator:       MySQL Workbench 5.2.31/ExportSQLite plugin 2009.12.02
-- Author:        Artem
-- Caption:       New Model
-- Project:       Name of the project
-- Changed:       2014-05-12 00:35
-- Created:       2014-05-08 01:02
-- PRAGMA foreign_keys = OFF;

-- Schema: qoals
-- BEGIN;
CREATE TABLE "user" (
  "id"   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "name" VARCHAR(45)                       NOT NULL
);
CREATE TABLE "goal" (
  "id"          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "title"       VARCHAR(45),
  "description" TEXT,
  "started_at"  INTEGER                           NOT NULL,
  "finish_at"   INTEGER                           NOT NULL,
  "category_id" INTEGER,
  "user_id"     INTEGER                           NOT NULL,
  "parent_id"   INTEGER,
  "list_index"  INTEGER,
  CONSTRAINT "fk_goal_user"
  FOREIGN KEY ("user_id")
  REFERENCES "user" ("id"),
  CONSTRAINT "fk_goal_goal1"
  FOREIGN KEY ("id", "parent_id")
  REFERENCES "goal" ("parent_id", "parent_id")
);
CREATE INDEX "goal.fk_goal_user" ON "goal" ("user_id");
CREATE INDEX "goal.fk_goal_goal1" ON "goal" ("id", "parent_id");
CREATE TABLE "notification" (
  "id"            INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "goal_id"       INTEGER                           NOT NULL,
  "repeat_number" INTEGER,
  "repeat_time"   INTEGER,
  "notify_at"     INTEGER                           NOT NULL,
  "sound_uri"     VARCHAR(100),
  CONSTRAINT "fk_notification_goal1"
  FOREIGN KEY ("goal_id")
  REFERENCES "goal" ("id")
  ON DELETE CASCADE
);
CREATE INDEX "notification.fk_notification_goal1" ON "notification" ("goal_id");
-- COMMIT;
