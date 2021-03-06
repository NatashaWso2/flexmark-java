package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.util.options.DataHolder;

public class DocxRendererOptions {
    public final boolean escapeHtmlBlocks;
    public final boolean escapeHtmlCommentBlocks;
    public final boolean escapeInlineHtml;
    public final boolean escapeInlineHtmlComments;
    public final boolean percentEncodeUrls;
    public final boolean suppressHtmlBlocks;
    public final boolean suppressHtmlCommentBlocks;
    public final boolean suppressInlineHtml;
    public final boolean suppressInlineHtmlComments;
    public final boolean tocGenerate;
    public final boolean logImageProcessing;
    public final boolean noCharacterStyles;
    public final int maxImageWidth;
    public final String tocInstruction;
    public final String codeHighlightShading;

    public DocxRendererOptions(DataHolder options) {
        escapeHtmlBlocks = DocxRenderer.ESCAPE_HTML_BLOCKS.getFrom(options);
        escapeHtmlCommentBlocks = DocxRenderer.ESCAPE_HTML_COMMENT_BLOCKS.getFrom(options);
        escapeInlineHtml = DocxRenderer.ESCAPE_INLINE_HTML.getFrom(options);
        escapeInlineHtmlComments = DocxRenderer.ESCAPE_INLINE_HTML_COMMENTS.getFrom(options);
        percentEncodeUrls = DocxRenderer.PERCENT_ENCODE_URLS.getFrom(options);
        suppressHtmlBlocks = DocxRenderer.SUPPRESS_HTML_BLOCKS.getFrom(options);
        suppressHtmlCommentBlocks = DocxRenderer.SUPPRESS_HTML_COMMENT_BLOCKS.getFrom(options);
        suppressInlineHtml = DocxRenderer.SUPPRESS_INLINE_HTML.getFrom(options);
        suppressInlineHtmlComments = DocxRenderer.SUPPRESS_INLINE_HTML_COMMENTS.getFrom(options);
        maxImageWidth = DocxRenderer.MAX_IMAGE_WIDTH.getFrom(options);
        tocInstruction = DocxRenderer.TOC_INSTRUCTION.getFrom(options);
        tocGenerate = DocxRenderer.TOC_GENERATE.getFrom(options);
        logImageProcessing = DocxRenderer.LOG_IMAGE_PROCESSING.getFrom(options);
        noCharacterStyles = DocxRenderer.NO_CHARACTER_STYLES.getFrom(options);
        codeHighlightShading = DocxRenderer.CODE_HIGHLIGHT_SHADING.getFrom(options);
    }
}
