<sp:applyLayout name="${page.layout}">
	<head>
		<g:if test="${page.metaDescription}">
			<meta name="description" content="${page.metaDescription}">
		</g:if>
		<g:if test="${page.metaKeywords}">
			<meta name="keywords" content="${page.metaKeywords}">
		</g:if>
	</head>
	<body>
	<g:each var="partial" in="${page.partials}">
			<content tag="${partial.symbolName}">
				<spudCms:block key="spud.cms.${page.urlName}.${partial.symbolName}">
					${raw(partial.render())}
				</spudCms:block>
			</content>
	</g:each>
	</body>
</sp:applyLayout>
