import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { SubdivisionUpdateComponent } from 'app/entities/subdivision/subdivision-update.component';
import { SubdivisionService } from 'app/entities/subdivision/subdivision.service';
import { Subdivision } from 'app/shared/model/subdivision.model';

describe('Component Tests', () => {
  describe('Subdivision Management Update Component', () => {
    let comp: SubdivisionUpdateComponent;
    let fixture: ComponentFixture<SubdivisionUpdateComponent>;
    let service: SubdivisionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [SubdivisionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SubdivisionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubdivisionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubdivisionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Subdivision(123);
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
        const entity = new Subdivision();
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
