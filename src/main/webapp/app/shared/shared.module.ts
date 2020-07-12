import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { OrmvahSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [OrmvahSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [OrmvahSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OrmvahSharedModule {
  static forRoot() {
    return {
      ngModule: OrmvahSharedModule
    };
  }
}
