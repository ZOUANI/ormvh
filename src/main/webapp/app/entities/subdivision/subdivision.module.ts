import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrmvahSharedModule } from 'app/shared/shared.module';
import { SubdivisionComponent } from './subdivision.component';
import { SubdivisionDetailComponent } from './subdivision-detail.component';
import { SubdivisionUpdateComponent } from './subdivision-update.component';
import { SubdivisionDeleteDialogComponent } from './subdivision-delete-dialog.component';
import { subdivisionRoute } from './subdivision.route';

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(subdivisionRoute)],
  declarations: [SubdivisionComponent, SubdivisionDetailComponent, SubdivisionUpdateComponent, SubdivisionDeleteDialogComponent],
  entryComponents: [SubdivisionDeleteDialogComponent],
})
export class OrmvahSubdivisionModule {}
