<sp:applyLayout name="${page.layout}">
	<head>
		<title>${page.name}</title>
		<meta property="og:title" content="${page.name}" />

		<g:if test="${page.metaDescription}">
			<meta name="description" content="${page.metaDescription}">
			<meta property="og:description" content="${page.metaDescription}" />

		</g:if>
		<g:if test="${page.metaKeywords}">
			<meta name="keywords" content="${page.metaKeywords}">
		</g:if>
		<g:if test="${page.metaNoIndex || page.metaNoFollow}">
			<meta name="robots" content="${sp.metaNoIndexNoFollow(noFollow:page.metaNoFollow, noIndex:page.metaNoIndex)}" />
		</g:if>
	</head>
	<body>

	<g:each var="partial" in="${page.partials}">
			<content tag="${partial.symbolName}">
				${raw(partial.render())}
			</content>
	</g:each>
	</body>
</sp:applyLayout>
