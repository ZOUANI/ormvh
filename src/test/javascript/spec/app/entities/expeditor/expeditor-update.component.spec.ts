import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { ExpeditorUpdateComponent } from 'app/entities/expeditor/expeditor-update.component';
import { ExpeditorService } from 'app/entities/expeditor/expeditor.service';
import { Expeditor } from 'app/shared/model/expeditor.model';

describe('Component Tests', () => {
  describe('Expeditor Management Update Component', () => {
    let comp: ExpeditorUpdateComponent;
    let fixture: ComponentFixture<ExpeditorUpdateComponent>;
    let service: ExpeditorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [ExpeditorUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ExpeditorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExpeditorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExpeditorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Expeditor(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Expeditor();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
