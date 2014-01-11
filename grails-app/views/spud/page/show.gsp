<sp:applyLayout name="${page.layout}">
	<head>
		<g:if test="${page.metaDescription}">
			<meta name="description" content="${page.metaDescription}">
		</g:if>
		<g:if test="${page.metaKeywords}">
			<meta name="keywords" content="${page.metaKeywords}">
		</g:if>
	</head>
	<g:each var="partial" in="${page.partials}">
		<%println "Rendering ${partial.symbolName}"%>
		<content tag="${partial.symbolName}">
			${raw(partial.contentProcessed)}
		</content>
	</g:each>

</sp:applyLayout>
