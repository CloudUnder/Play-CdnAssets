# CdnAssets for Play Framework 2.x (Scala)

[CdnAssets.scala](CdnAssets.scala) is a simple replacement for [Play Framework](https://github.com/playframework/playframework)’s [Assets.at()](https://www.playframework.com/documentation/2.3.x/Assets) to enable loading static assets such as JavaScript, CSS, images etc. from a content delivery network (e.g. CloudFront). Whilst in development mode assets are loaded from localhost with an added timestamp as query string to prevent assets from being cached.

`CdnAssets` has been used successfully with Play Framework 2.2 and 2.3.

## Instructions

The following instructions apply to a standard Play Framework 2.x installation.

* Copy `CdnAssets.scala` to `/app/controllers/`.
* In your views replace `@Assets.at("filename")` with `@CdnAssets.at("filename")` (see [sample-view.scala.html](sample-view.scala.html)).
* Add `assetscdn="//xxxxxxxxxxx.cloudfront.net"` and `assetsversion="1"` to your `/conf/application.conf` (see [sample.conf](sample.conf)).
* Make sure your development assets are stored in `/public/dev/`.
* Make sure your production assets are stored in `/public/1/`.

Whenever you deploy a new version of your assets, bump the `assetsversion` number in the config file and store the new assets in `/public/{version number}/`. A Grunt task can make this a simple task.

The benefits of putting an assets version number into the path is, old assets will no longer be loaded from cache – neither from browser cache nor from the CDN’s caches. You don’t have to invalidate the objects of the CDN, which can take a very long time and may involve cost. You can increase `max-age` cache headers of your assets for better performance.

## Limitations

There is no equivalent for `Assets.at(path: String, file: String)`.
