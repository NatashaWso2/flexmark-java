---
title: Attributes Extension
author:
version:
date: '2017-12-20'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Attributes

Converts attributes `{...}` syntax into attributes AST nodes and adds an attribute provider to
set attributes for immediately preceding node sibling node.

If the preceding sibling node is `Text` then the attributes are applied to the parent node.

If the attributes node is the first node of a paragraph then the attributes will be applied to
the paragraph's parent node. Effectively putting attributes element first in a paragraph allows
you to set the attributes of list items, block quotes or other paragraph containers.

The attributes is a space separated list of attribute syntax of one of the following:

* `name=value`
* `name='value'`
* `name="value"`
* `#id`
* `.class`

AttributesNode which start with `#` such as `#id-string` are equivalent to `id="id-string"` and
`.class-name` are equivalent to `class="class-name"`

NOTE: class values are accumulated as a space separated list. Other attributes override any
previous values.

```````````````````````````````` example Attributes: 1
Sample text{.class-name}
.
<p class="class-name">Sample text</p>
.
Document[0, 25]
  Paragraph[0, 25]
    Text[0, 11] chars:[0, 11, "Sampl …  text"]
    AttributesNode[11, 24] textOpen:[11, 12, "{"] text:[12, 23, ".class-name"] textClose:[23, 24, "}"]
      AttributeNode[12, 23] name:[12, 13, "."] value:[13, 23, "class-name"] isImplicit isClass
````````````````````````````````


```````````````````````````````` example Attributes: 2
Paragraph{style="color:red"}
.
<p style="color:red">Paragraph</p>
.
Document[0, 29]
  Paragraph[0, 29]
    Text[0, 9] chars:[0, 9, "Paragraph"]
    AttributesNode[9, 28] textOpen:[9, 10, "{"] text:[10, 27, "style=\"color:red\""] textClose:[27, 28, "}"]
      AttributeNode[10, 27] name:[10, 15, "style"] sep:[15, 16, "="] valueOpen:[16, 17, "\""] value:[17, 26, "color:red"] valueClose:[26, 27, "\""]
````````````````````````````````


```````````````````````````````` example Attributes: 3
Sample text **bold**{.class-name}
.
<p>Sample text <strong class="class-name">bold</strong></p>
.
Document[0, 34]
  Paragraph[0, 34]
    Text[0, 12] chars:[0, 12, "Sampl … text "]
    StrongEmphasis[12, 20] textOpen:[12, 14, "**"] text:[14, 18, "bold"] textClose:[18, 20, "**"]
      Text[14, 18] chars:[14, 18, "bold"]
    AttributesNode[20, 33] textOpen:[20, 21, "{"] text:[21, 32, ".class-name"] textClose:[32, 33, "}"]
      AttributeNode[21, 32] name:[21, 22, "."] value:[22, 32, "class-name"] isImplicit isClass
````````````````````````````````


```````````````````````````````` example Attributes: 4
Sample text **bold{.class-name}**
.
<p>Sample text <strong class="class-name">bold</strong></p>
.
Document[0, 34]
  Paragraph[0, 34]
    Text[0, 12] chars:[0, 12, "Sampl … text "]
    StrongEmphasis[12, 33] textOpen:[12, 14, "**"] text:[14, 31, "bold{.class-name}"] textClose:[31, 33, "**"]
      Text[14, 18] chars:[14, 18, "bold"]
      AttributesNode[18, 31] textOpen:[18, 19, "{"] text:[19, 30, ".class-name"] textClose:[30, 31, "}"]
        AttributeNode[19, 30] name:[19, 20, "."] value:[20, 30, "class-name"] isImplicit isClass
````````````````````````````````


```````````````````````````````` example Attributes: 5
Sample text ![Sample Image](http://example.com){width=64 height=32}
.
<p>Sample text <img src="http://example.com" alt="Sample Image" width="64" height="32" /></p>
.
Document[0, 68]
  Paragraph[0, 68]
    Text[0, 12] chars:[0, 12, "Sampl … text "]
    Image[12, 47] textOpen:[12, 14, "!["] text:[14, 26, "Sample Image"] textClose:[26, 27, "]"] linkOpen:[27, 28, "("] url:[28, 46, "http://example.com"] pageRef:[28, 46, "http://example.com"] linkClose:[46, 47, ")"]
      Text[14, 26] chars:[14, 26, "Sampl … Image"]
    AttributesNode[47, 67] textOpen:[47, 48, "{"] text:[48, 66, "width=64 height=32"] textClose:[66, 67, "}"]
      AttributeNode[48, 56] name:[48, 53, "width"] sep:[53, 54, "="] value:[54, 56, "64"]
      AttributeNode[57, 66] name:[57, 63, "height"] sep:[63, 64, "="] value:[64, 66, "32"]
````````````````````````````````


```````````````````````````````` example Attributes: 6
* list item{style="color:red"}
* {style="color:blue"}list item
.
<ul>
  <li><span style="color:red">list item</span></li>
  <li style="color:blue"><span>list item</span></li>
</ul>
.
Document[0, 63]
  BulletList[0, 63] isTight
    BulletListItem[0, 31] open:[0, 1, "*"] isTight
      Paragraph[2, 31]
        Text[2, 11] chars:[2, 11, "list item"]
        AttributesNode[11, 30] textOpen:[11, 12, "{"] text:[12, 29, "style=\"color:red\""] textClose:[29, 30, "}"]
          AttributeNode[12, 29] name:[12, 17, "style"] sep:[17, 18, "="] valueOpen:[18, 19, "\""] value:[19, 28, "color:red"] valueClose:[28, 29, "\""]
    BulletListItem[31, 63] open:[31, 32, "*"] isTight
      Paragraph[33, 63]
        AttributesNode[33, 53] textOpen:[33, 34, "{"] text:[34, 52, "style=\"color:blue\""] textClose:[52, 53, "}"]
          AttributeNode[34, 52] name:[34, 39, "style"] sep:[39, 40, "="] valueOpen:[40, 41, "\""] value:[41, 51, "color:blue"] valueClose:[51, 52, "\""]
        Text[53, 62] chars:[53, 62, "list item"]
````````````````````````````````


```````````````````````````````` example Attributes: 7
# Heading {#custom-id}
.
<h1 id="custom-id">Heading </h1>
.
Document[0, 23]
  Heading[0, 22] textOpen:[0, 1, "#"] text:[2, 22, "Heading {#custom-id}"]
    Text[2, 10] chars:[2, 10, "Heading "]
    AttributesNode[10, 22] textOpen:[10, 11, "{"] text:[11, 21, "#custom-id"] textClose:[21, 22, "}"]
      AttributeNode[11, 21] name:[11, 12, "#"] value:[12, 21, "custom-id"] isImplicit isId
````````````````````````````````


```````````````````````````````` example(Attributes: 8) options(anchors)
# Heading {#custom-id}
.
<h1><a href="#custom-id" id="custom-id"></a>Heading </h1>
.
Document[0, 22]
  Heading[0, 22] textOpen:[0, 1, "#"] text:[2, 22, "Heading {#custom-id}"]
    AnchorLink[2, 2]
    Text[2, 10] chars:[2, 10, "Heading "]
    AttributesNode[10, 22] textOpen:[10, 11, "{"] text:[11, 21, "#custom-id"] textClose:[21, 22, "}"]
      AttributeNode[11, 21] name:[11, 12, "#"] value:[12, 21, "custom-id"] isImplicit isId
````````````````````````````````


```````````````````````````````` example Attributes: 9
# Heading # {#custom-id}
.
<h1 id="custom-id">Heading # </h1>
.
Document[0, 25]
  Heading[0, 24] textOpen:[0, 1, "#"] text:[2, 24, "Heading # {#custom-id}"]
    Text[2, 12] chars:[2, 12, "Heading # "]
    AttributesNode[12, 24] textOpen:[12, 13, "{"] text:[13, 23, "#custom-id"] textClose:[23, 24, "}"]
      AttributeNode[13, 23] name:[13, 14, "#"] value:[14, 23, "custom-id"] isImplicit isId
````````````````````````````````


```````````````````````````````` example Attributes: 10
Heading {#custom-id}
======================
.
<h1 id="custom-id">Heading </h1>
.
Document[0, 44]
  Heading[0, 43] text:[0, 20, "Heading {#custom-id}"] textClose:[21, 43, "======================"]
    Text[0, 8] chars:[0, 8, "Heading "]
    AttributesNode[8, 20] textOpen:[8, 9, "{"] text:[9, 19, "#custom-id"] textClose:[19, 20, "}"]
      AttributeNode[9, 19] name:[9, 10, "#"] value:[10, 19, "custom-id"] isImplicit isId
````````````````````````````````


```````````````````````````````` example Attributes: 11
Heading {#123-heading}
======================
.
<h1 id="123-heading">Heading </h1>
.
Document[0, 46]
  Heading[0, 45] text:[0, 22, "Heading {#123-heading}"] textClose:[23, 45, "======================"]
    Text[0, 8] chars:[0, 8, "Heading "]
    AttributesNode[8, 22] textOpen:[8, 9, "{"] text:[9, 21, "#123-heading"] textClose:[21, 22, "}"]
      AttributeNode[9, 21] name:[9, 10, "#"] value:[10, 21, "123-heading"] isImplicit isId
````````````````````````````````


```````````````````````````````` example Attributes: 12
Heading
=======
.
<h1 id="heading">Heading</h1>
.
Document[0, 16]
  Heading[0, 15] text:[0, 7, "Heading"] textClose:[8, 15, "======="]
    Text[0, 7] chars:[0, 7, "Heading"]
````````````````````````````````


```````````````````````````````` example Attributes: 13
Heading with emoji :+1:
=======================
.
<h1 id="heading-with-emoji">Heading with emoji <img src="/img/plus1.png" alt="emoji people:+1" height="20" width="20" align="absmiddle" /></h1>
.
Document[0, 48]
  Heading[0, 47] text:[0, 23, "Heading with emoji :+1:"] textClose:[24, 47, "======================="]
    Text[0, 19] chars:[0, 19, "Headi … moji "]
    Emoji[19, 23] textOpen:[19, 20, ":"] text:[20, 22, "+1"] textClose:[22, 23, ":"]
      Text[20, 22] chars:[20, 22, "+1"]
````````````````````````````````


## TOC

Default rendering with emphasis

```````````````````````````````` example TOC: 1
[TOC] 

# Heading **some bold** 1 {#heading-1}
## Heading 1.1 _some italic_ {#heading-2}
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_** {#heading-3}
.
<ul>
  <li><a href="#heading-2">Heading 1.1 <em>some italic</em></a>
    <ul>
      <li><a href="#heading-111">Heading 1.1.1</a></li>
      <li><a href="#heading-3">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
    </ul>
  </li>
</ul>
<h1 id="heading-1">Heading <strong>some bold</strong> 1 </h1>
<h2 id="heading-2">Heading 1.1 <em>some italic</em> </h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-3">Heading 1.1.2  <strong><em>some bold italic</em></strong> </h3>
.
Document[0, 162]
  TocBlock[0, 7] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 5]
  Heading[8, 46] textOpen:[8, 9, "#"] text:[10, 46, "Heading **some bold** 1 {#heading-1}"]
    Text[10, 18] chars:[10, 18, "Heading "]
    StrongEmphasis[18, 31] textOpen:[18, 20, "**"] text:[20, 29, "some bold"] textClose:[29, 31, "**"]
      Text[20, 29] chars:[20, 29, "some bold"]
    Text[31, 34] chars:[31, 34, " 1 "]
    AttributesNode[34, 46] textOpen:[34, 35, "{"] text:[35, 45, "#heading-1"] textClose:[45, 46, "}"]
      AttributeNode[35, 45] name:[35, 36, "#"] value:[36, 45, "heading-1"] isImplicit isId
  Heading[47, 88] textOpen:[47, 49, "##"] text:[50, 88, "Heading 1.1 _some italic_ {#heading-2}"]
    Text[50, 62] chars:[50, 62, "Headi …  1.1 "]
    Emphasis[62, 75] textOpen:[62, 63, "_"] text:[63, 74, "some italic"] textClose:[74, 75, "_"]
      Text[63, 74] chars:[63, 74, "some  … talic"]
    Text[75, 76] chars:[75, 76, " "]
    AttributesNode[76, 88] textOpen:[76, 77, "{"] text:[77, 87, "#heading-2"] textClose:[87, 88, "}"]
      AttributeNode[77, 87] name:[77, 78, "#"] value:[78, 87, "heading-2"] isImplicit isId
  Heading[89, 106] textOpen:[89, 92, "###"] text:[93, 106, "Heading 1.1.1"]
    Text[93, 106] chars:[93, 106, "Headi … 1.1.1"]
  Heading[107, 161] textOpen:[107, 110, "###"] text:[111, 161, "Heading 1.1.2  **_some bold italic_** {#heading-3}"]
    Text[111, 126] chars:[111, 126, "Headi … 1.2  "]
    StrongEmphasis[126, 148] textOpen:[126, 128, "**"] text:[128, 146, "_some bold italic_"] textClose:[146, 148, "**"]
      Emphasis[128, 146] textOpen:[128, 129, "_"] text:[129, 145, "some bold italic"] textClose:[145, 146, "_"]
        Text[129, 145] chars:[129, 145, "some  … talic"]
    Text[148, 149] chars:[148, 149, " "]
    AttributesNode[149, 161] textOpen:[149, 150, "{"] text:[150, 160, "#heading-3"] textClose:[160, 161, "}"]
      AttributeNode[150, 160] name:[150, 151, "#"] value:[151, 160, "heading-3"] isImplicit isId
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
Paragraph{style="color:red"}
.
<p md-pos="0-28" style="color:red">Paragraph</p>
.
Document[0, 28]
  Paragraph[0, 28]
    Text[0, 9] chars:[0, 9, "Paragraph"]
    AttributesNode[9, 28] textOpen:[9, 10, "{"] text:[10, 27, "style=\"color:red\""] textClose:[27, 28, "}"]
      AttributeNode[10, 27] name:[10, 15, "style"] sep:[15, 16, "="] valueOpen:[16, 17, "\""] value:[17, 26, "color:red"] valueClose:[26, 27, "\""]
````````````````````````````````


