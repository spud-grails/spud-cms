<g:applyLayout name="${page.layout}">
	<g:each var="partial" in="${page.partials}">
		<content tag="${partial.symbolName}">
			${partial.contentProcessed}
		</content>
	</g:each>
</g:applyLayout>
