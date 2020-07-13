/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OrmvahTestModule } from '../../../test.module';
import { ExpeditorTypeDeleteDialogComponent } from 'app/entities/expeditor-type/expeditor-type-delete-dialog.component';
import { ExpeditorTypeService } from 'app/entities/expeditor-type/expeditor-type.service';

describe('Component Tests', () => {
  describe('ExpeditorType Management Delete Component', () => {
    let comp: ExpeditorTypeDeleteDialogComponent;
    let fixture: ComponentFixture<ExpeditorTypeDeleteDialogComponent>;
    let service: ExpeditorTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [ExpeditorTypeDeleteDialogComponent]
      })
        .overrideTemplate(ExpeditorTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExpeditorTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExpeditorTypeService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
