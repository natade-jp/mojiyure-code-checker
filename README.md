# 文字揺れコードチェッカー

## 説明

本プログラムは、ソースコードに存在する文字揺れ／表記ゆれを検知するオープンソースのツールです。スタンドアロンで動作し、外部へ情報を流しません。

- Java 11 以上をインストールした環境で利用できます。
- 本ソフトウェアはマルチプラットフォームとなりますが、現在 Windows環境用のバッチしか用意されておりません。

## インストール方法

Windows環境にてJava 11 以上をインストールした後に以下を実行してください。実行が完了すると `build` フォルダに成果物が作られます。

1. `./bin/clean.bat`
2. `./bin/build-extract-text.bat`
3. `./bin/clean.bat`
4. `./bin/build-mojiyure-codecheker.bat`

## 利用方法

以下の2つの実行ファイルを利用します。
順番に実行することで表記ゆれを検知できます。

- `./bin/start-extract-text.bat`
  - ExtractText というツールで、ソースコードから文字列のみを抽出します。
- `./bin/start-mojiyure-codecheker.bat`
  - MojiyureCodeChecker というツールで、文字列のテキストから文字揺れを検出します。

### ExtractText

以下の引数を利用できます。

- `-src` ソースコードが置いてあるフォルダ（必須）
- `-out` 抜き出した文字列ファイル(`*.string`)を出力するフォルダ（必須）
- `-extensin_c` C言語系の拡張子の設定（任意）
  - 省略した場合はC/CPPなどのソースコードが選ばれる
  - 指定する場合は `\\.(c|cpp|cs|java|js|json)$` のように正規表現で設定すること
- `-extensin_html` HTML言語系の拡張子の設定（任意）
  - 省略した場合はHTML/XMLなどのソースコードが選ばれる

### MojiyureCodeChecker

以下の引数を利用できます。

- `-src` 文字列ファイル(`*.string`)が置いてあるフォルダ（必須）

## 利用しているライブラリ

本ソフトウェアは、複数のライブラリを使用しております。ライブラリは、`./libs/` 配下にライセンスファイル、`NOTICE`ファイル等とともにおいてあります。

### [kuromoji-unidic](https://github.com/atilika/kuromoji)

Atilikaが開発したJavaのオープンソース日本語形態エンジンです。`Apache License 2.0`です。内部では、`unidic-mecab-2.1.2_src`が用いられており、以下のライセンスとなっております。

> ### [MeCab](https://taku910.github.io/mecab/)
> 京大情報学研究科−NTT科学基礎研究所 共同研究ユニットプロジェクトにて開発した形態素解析エンジンです。[BSD,LGPL,GPLのトリプルライセンス](https://taku910.github.io/mecab/)です。`unidic-mecab-2.1.2_src` で使用されています。

> ### [UniDic](https://ccd.ninjal.ac.jp/unidic/)
> コーパス開発センターが公開している辞書です。[BSD,LGPL,GPLのトリプルライセンス](https://ccd.ninjal.ac.jp/unidic/faq#license)です。`unidic-mecab-2.1.2_src` で使用されています。

### [GSON](https://github.com/google/gson)

Googleが開発したJavaのJSONライブラリです。`Apache License 2.0`です。

## Author
- [natade-jp](https://github.com/natade-jp/)
